package expenses.android.com.expenses;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import expenses.android.com.expenses.data.ExpenseDBHelper;

/**
 * @author Expense Group
 *
 *         SettingsFragment class extends the Fragment class. It is used for creating,
 *         reading,updating,deleteing the Application's Settings.
 */
public class SettingsFragment extends Fragment {

    View theView;
    EditText txtBudget;
    Button btnApply;
    Button btnReset;
    Float amount;
    Float budget;
    SharedPreferences sharedPref;
    private ExpenseDBHelper mExpenseDBHelper;

    /**
     * Inflate the fragment within the activity being used
     *
     * @return theView containing the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_settings, container, false);
        getActivity().setTitle(getActivity().getString(R.string.settings_title));

        txtBudget = (EditText) theView.findViewById(R.id.txtBudget);
        btnApply = (Button) theView.findViewById(R.id.btnApply);
        btnReset = (Button) theView.findViewById(R.id.btnReset);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        txtBudget.setText(String.valueOf(sharedPref.getFloat("BUDGET", 0)));

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                budget = Float.valueOf(txtBudget.getText().toString());

                amount = budget - ((MainActivity) getActivity()).getTotalCost();
                if (amount > 0) {
                    sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat("REMAINING", amount);
                    editor.apply();

                    editor.putFloat("BUDGET", budget);
                    editor.apply();

                    // change the value in Mainactivity
                    ((MainActivity) getActivity()).setRemainingAmount(amount);
                    ((MainActivity) getActivity()).setBudgetAmount(budget);


                    Toast.makeText(getActivity(), "Budget Set:" + budget,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Budget is less than total cost. Change the Budget",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("REMAINING", 1000);
                editor.apply();

                editor.putFloat("BUDGET", 1000);
                editor.apply();

                editor.putFloat("TOTAL", 0);
                editor.apply();

                txtBudget.setText("1000");

                // change the value in Mainactivity
                ((MainActivity) getActivity()).setRemainingAmount(1000);
                ((MainActivity) getActivity()).setBudgetAmount(1000);

                ((MainActivity) getActivity()).setTotalCost(0f);

                //Delete All Expenses
                mExpenseDBHelper = new ExpenseDBHelper(getActivity().getApplicationContext());
                mExpenseDBHelper.deleteAll();


                Toast.makeText(getActivity(), "Values Reset",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return theView;
    }

}
