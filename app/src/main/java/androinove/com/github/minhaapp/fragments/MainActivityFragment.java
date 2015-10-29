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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.HttpURLConnection;

import androinove.com.github.minhaapp.R;
import androinove.com.github.minhaapp.adapter.AdapterListViewUsuarios;
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
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.btn_listar_usuarios) Button btnListarUsuarios;
    @Bind(R.id.list_view_usuarios) ListView listViewUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(MainActivityFragment.this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    @OnClick(R.id.btn_listar_usuarios)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listar_usuarios:
                AsyncTaskListarUsuarios asyncTaskListarUsuarios = new AsyncTaskListarUsuarios();
                asyncTaskListarUsuarios.execute(Constants.URL_LISTAR_CADASTRAR_USUARIOS);
                break;
        }
    }

    public void respostaParaArrayUsuarios(String resposta) {
        if (resposta != null) {
            Usuario[] usuarios = new Gson().fromJson(resposta, Usuario[].class);
            exibirUsuariosNaTela(usuarios);
        } else {
            exibirErro();
        }
    }

    public void exibirUsuariosNaTela(Usuario[] usuarios) {
        listViewUsuarios.setAdapter(new AdapterListViewUsuarios(getActivity(), usuarios));
    }

    public void exibirErro() {
        Toast.makeText(getActivity(), "Ocorreu algum erro", Toast.LENGTH_SHORT).show();
    }

    public class AsyncTaskListarUsuarios extends AsyncTask<String, Void, String> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.progress_dialog_title), getString(R.string.progress_dialog_message), false, false);
            Log.d(Constants.LOG_KEY, "onPreExecute()");
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {
            Log.d(Constants.LOG_KEY, "doInBackground()");
            HttpURLConnection httpURLConnection = null;
            String respostaDoServidor = null;

            try {
                httpURLConnection = ConnectionUtil.connect(params[0], Constants.HTTP_METHOD_GET, false, null);

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    respostaDoServidor = StringUtil.streamToString(httpURLConnection.getInputStream());
                }
            } catch (Exception e) {
                Log.e(Constants.LOG_KEY, "AsyncTaskListarUsuarios.doInBackground -> " + e.getMessage());
            } finally {
                ConnectionUtil.closeConnection(httpURLConnection);
            }

            return respostaDoServidor;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(Constants.LOG_KEY, "onPostExecute() -> " + s);
            super.onPostExecute(s);
            respostaParaArrayUsuarios(s);
            this.mProgressDialog.dismiss();
        }
    }

}
