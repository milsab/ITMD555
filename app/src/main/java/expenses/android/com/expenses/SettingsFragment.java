package expenses.android.com.expenses;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    int amount;
    int budget;

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

                amount = Integer.parseInt(txtBudget.getText().toString());
                budget=amount;

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("REMAINING", amount);
                editor.apply();

                editor.putInt("BUDGET", budget);
                editor.apply();

                // change the value in Mainactivity
                ((MainActivity) getActivity()).setRemainingAmount(amount);
                ((MainActivity) getActivity()).setRemainingAmount(budget);


                Toast.makeText(getActivity(), "budget set:" + budget,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return theView;
    }

}
