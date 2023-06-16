package br.edu.infnet.meusgastos.repository

import br.edu.infnet.meusgastos.models.Despesa
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val TAG = "DespesasFirebase"

class DespesasRepository private constructor(){

    companion object {

        lateinit var auth: FirebaseAuth

        lateinit var db: FirebaseFirestore

        lateinit var colecaoDespesas: CollectionReference

        lateinit var colecaoCategorias: CollectionReference


        private var INSTANCE: DespesasRepository? = null
        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = DespesasRepository()
            }

            //Autorização firebase(cadastro e login)
            auth = Firebase.auth

            // Banco de dados Firestore
            db = Firebase.firestore

            // Coleção de despesas:
            colecaoDespesas = db.collection(auth.currentUser.toString())

            colecaoCategorias = db.collection("categorias")

        }

        fun get(): DespesasRepository {
            return INSTANCE
                ?: throw IllegalStateException("DespesasRepository deve ser inicializado.")
        }
    }

        // Auth /////////////////

        fun getCurrentUser() = auth.currentUser

        fun isLoggedIn(): Boolean {

            if(getCurrentUser() != null) {
                return true
            }
            return false
        }

        fun atualizaUsuarioAtual(){
            if (getCurrentUser() != null) {
                auth.currentUser?.reload()
            }
        }
        // Faça o mesmo que foi feito com o Login
        fun cadastrarUsuarioComSenha(
            email: String,
            password: String
        ) : Task<AuthResult> {
            return auth.createUserWithEmailAndPassword(email, password)
        }

        fun login(
            email: String,
            password: String
        ) : Task<AuthResult> {
  //          auth.currentUser?.reload()
            return auth.signInWithEmailAndPassword(email, password)
        }

        fun logout(){
            auth.signOut()
        }

        // FireStore ///////////////////////////////////////////////////////////////////////////////////

        fun criarDespesa( despesa: Despesa): Task<DocumentReference> {
            return  colecaoDespesas.add(despesa)
        }

        fun getDespesas() : Task<QuerySnapshot> {
            return colecaoDespesas.get()
        }

        fun getDespesasColecao(): CollectionReference {
            return colecaoDespesas
        }

        fun deleteDespesa(id: String) {
            colecaoDespesas.document(id).delete()
        }

        fun atualizaDespesa(id: String?, despesa: Despesa) {
            if (id != null){
                colecaoDespesas.document(id).set(despesa)
            }
        }
        

}