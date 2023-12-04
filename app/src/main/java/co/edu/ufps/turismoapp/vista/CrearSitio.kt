package co.edu.ufps.turismoapp.vista

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.edu.ufps.turismoapp.R
import co.edu.ufps.turismoapp.modelo.Sitio
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CrearSitio : AppCompatActivity() {
    lateinit var foto: ImageView
    lateinit var nombre: TextInputEditText
    lateinit var descripcion: TextInputEditText
    lateinit var localizar: Button
    lateinit var guardar: Button
    lateinit var capturar: Button
    val TAG: String = "CrearSitio"
    private lateinit var viewFinder: PreviewView
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraProvider: ProcessCameraProvider
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sitio)
        foto = findViewById(R.id.fotoimagen)
        nombre = findViewById(R.id.nombretienda)
        descripcion = findViewById(R.id.descripciontienda)
        localizar = findViewById(R.id.localizar)
        guardar = findViewById(R.id.guardar)
        capturar = findViewById(R.id.captura)
        viewFinder = findViewById(R.id.viewFinder)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
      //  outputDirectory = getOutputDirectory()
        capturar.setOnClickListener {
            takePhoto()

        }

        guardar.setOnClickListener {
            guardarImagen()

        }

        localizar.setOnClickListener {
           // startCamera()

        }

    }



    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        imageCapture = ImageCapture.Builder().build()
        cameraProviderFuture.addListener({
            // Se usa para vincular el ciclo de vida de las cámaras al del propietario del ciclo de vida
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Previsualización
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            // Selector de cámara predeterminado
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Desvincula todas las cámaras de uso anterior
                cameraProvider.unbindAll()

                // Vincula la cámara con el ciclo de vida del propietario
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageCapture)
            } catch(exc: Exception) {
                Log.e(TAG, "Error al vincular el ciclo de vida de la cámara---", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val savedUri = output.savedUri ?: return
                    imageUri = savedUri
                    foto.post {
                        foto.setImageURI(savedUri)
                    }
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    viewFinder.setVisibility(View.GONE);
                }
            }
        )
    }

    private companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        }
    }

  private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permisos no concedidos por el usuario.",
                    Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad si los permisos no se conceden
            }
        }
    }

    private fun stopCamera() {
        cameraProvider.unbindAll()
    }

    private fun guardar(imageUrl: String) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.reference
        val sitioTuristico = Sitio(myRef.push().key.toString(),nombre.text.toString(),descripcion.text.toString(),imageUrl,0.0,0.0)
        myRef.child("sitios").child(sitioTuristico.id).setValue(sitioTuristico)
        finish()
    }

    private fun guardarImagen() {
        imageUri?.let { uri ->
            val storageReference = FirebaseStorage.getInstance().reference.child("images/${uri.lastPathSegment}")
            val uploadTask = storageReference.putFile(uri)

            uploadTask.addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    val imageUrl = downloadUri.toString()
                    guardar(imageUrl) // Guarda la URL junto con otros datos en Firestore
                }
            }.addOnFailureListener {
                Log.e("FirebaseStorage", "Error al subir imagen", it)
                Toast.makeText(this,
                    "Error subida. ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this,
                "Imagen Nula",
                Toast.LENGTH_SHORT).show()
            // Maneja el caso en que imageUri sea null, es decir, cuando no se ha capturado ninguna imagen
        }
    }



}