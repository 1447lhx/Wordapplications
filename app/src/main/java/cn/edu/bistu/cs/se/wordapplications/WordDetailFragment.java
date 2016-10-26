package cn.edu.bistu.cs.se.wordapplications;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WordDetailFragment extends Fragment {
    private Bundle args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_detail, container, false);
        TextView tv_word = (TextView) view.findViewById(R.id.tv_detail_word);
        TextView tv_meaning = (TextView) view.findViewById(R.id.tv_detail_word_meaning);
        TextView tv_sample = (TextView) view.findViewById(R.id.tv_detail_word_sample);
        if (args != null) {
            Word w = (Word) args.getSerializable("word");
            if(w != null){
                tv_word.setText(w.getWord());
                tv_meaning.setText(w.getMeaning());
                tv_sample.setText(w.getSample());
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
