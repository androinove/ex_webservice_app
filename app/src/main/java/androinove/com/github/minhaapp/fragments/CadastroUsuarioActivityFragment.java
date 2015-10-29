package androinove.com.github.minhaapp.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import androinove.com.github.minhaapp.R;
import androinove.com.github.minhaapp.entity.Usuario;
import androinove.com.github.minhaapp.util.ConnectionUtil;
import androinove.com.github.minhaapp.util.Constants;
import androinove.com.github.minhaapp.util.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class CadastroUsuarioActivityFragment extends Fragment implements View.OnClickListener {

    private View view;
    @Bind(R.id.edt_nome) EditText edtNome;
    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.edt_telefone) EditText edtTelefone;
    @Bind(R.id.btn_cadastrar) Button btnConcluir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_usuario, container, false);
        ButterKnife.bind(CadastroUsuarioActivityFragment.this, view);
        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    @OnClick(R.id.btn_cadastrar)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cadastrar:
                finalizarCadastro();
                break;
        }
    }

    public void finalizarCadastro() {
        Usuario usuario = new Usuario(edtNome.getText().toString(), edtEmail.getText().toString(), edtTelefone.getText().toString());

        if (usuario.existeCamposInvalidos()) {
            Snackbar.make(view, "Alguns campos estão vazios ou inválidos", Snackbar.LENGTH_SHORT).show();
        } else {
            AsyncTaskCadastrarUsuario asyncTaskCadastrarUsuario = new AsyncTaskCadastrarUsuario(usuario);
            asyncTaskCadastrarUsuario.execute(Constants.URL_LISTAR_CADASTRAR_USUARIOS);
        }
    }

    public void showResult(String s) {
        Toast.makeText(getActivity(), "Resposta do Servidor: " + s, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    public class AsyncTaskCadastrarUsuario extends AsyncTask<String, Void, String> {

        private Usuario mUsuario;
        private ProgressDialog mProgressDialog;

        public AsyncTaskCadastrarUsuario(Usuario usuario) {
            this.mUsuario = usuario;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.progress_dialog_title), getString(R.string.progress_dialog_message), false, false);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection httpURLConnection;
            String resposta = null;
            byte[] postData;

            postData = StringUtil.montarURL(this.mUsuario).getBytes(Charset.forName("UTF-8"));
            httpURLConnection = ConnectionUtil.connect(params[0], Constants.HTTP_METHOD_POST, true, postData);

            try {
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    resposta = StringUtil.streamToString(httpURLConnection.getInputStream());
                }
            } catch (Exception e) {
                Log.e(Constants.LOG_KEY, "AsyncTaskCadastrarUsuario.doInBackground -> " + e.getMessage());
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.mProgressDialog.dismiss();
            showResult(s);
        }
    }

}
