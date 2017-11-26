package expenses.android.com.expenses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExpenseFragment extends Fragment {

    View theView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_expense, container, false);
        getActivity().setTitle(getActivity().getString(R.string.expense_title));
        return theView;
    }
}
