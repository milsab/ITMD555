package expenses.android.com.expenses;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    View theView;

    TextView mAboutTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_about, container, false);
        getActivity().setTitle(getActivity().getString(R.string.about_title));

        mAboutTextView = (TextView)theView.findViewById(R.id.about_text_view);
        mAboutTextView.setText(getActivity().getString(R.string.about_text));
        return theView;
    }
}
