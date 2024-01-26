package fr.ecrinsdespatule.skirandobriancon

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.databaseRef
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.downloadUri
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.meteoList
import fr.ecrinsdespatule.skirandobriancon.MeteoRepository.Singleton.storageReference
import java.util.UUID

class MeteoRepository {

    object Singleton {
        //pouvoir se connecter à la référence 'meteo'
        val databaseRef = FirebaseDatabase.getInstance().getReference("meteo")

        //créer une liste qui va contenir nos meteos
        val meteoList = arrayListOf<MeteoModel>()

        //Donner le lien d'accès au BUCKET
        private val BUCKET_URL: String = "gs://skirandobriancon.appspot.com/"

        //Se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //Var qui contient le lien de l'image courante
        var downloadUri: Uri? = null
    }
    fun updateData(callback: ()-> Unit) {
     //absorber les données depuis la databaseRef -> liste de meteo
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes
                meteoList.clear()

                //récolter la liste
                for (ds in snapshot.children) {
                    //construire un objet météo
                    val meteo = ds.getValue(MeteoModel::class.java)

                    //vérifier que la météo n'est pas nulle
                    if (meteo != null) {
                        //ajouter la météo à notre liste
                        meteoList.add(meteo)
                    }
                }
             //actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    //créer une fonction pour envoyer des fichiers au storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        //vérifier que ce fichier ne soit pas nul
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //démarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                //Si il y a eu un problème avec le fichier
                if(!task.isSuccessful){
                    task.exception?.let {throw it }
                }
                return@Continuation ref.downloadUrl
                }).addOnCompleteListener { task ->
                //Vérification que tout fonctionne
                if(task.isSuccessful) {
                    //Récupérer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }

    }

    //mettre à jour un objet meteo en BDD
    fun updateMeteo(meteo: MeteoModel) = databaseRef.child(meteo.id).setValue(meteo)

    //Insérer une nouvelle meteo en BDD
    fun insertMeteo(meteo: MeteoModel) = databaseRef.child(meteo.id).setValue(meteo)

    //supprimer une météo de la BDD
    //fun deleteMeteo(plant: MeteoModel) = databaseRef.child(plant.id).removeValue()
}
