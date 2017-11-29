package expenses.android.com.expenses;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    View theView;
    EditText txtBudget;
    Button btnApply;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_settings, container, false);
        getActivity().setTitle(getActivity().getString(R.string.settings_title));

        txtBudget = (EditText) theView.findViewById(R.id.txtBudget);
        btnApply = (Button) theView.findViewById(R.id.btnApply);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int amount = Integer.parseInt(txtBudget.getText().toString());
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("REMAINING", amount);
                editor.commit();

                ((MainActivity) getActivity()).setRemainingAmount(amount);

                Toast.makeText(getActivity(), "Your Budget Applyied Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return theView;
    }


}
