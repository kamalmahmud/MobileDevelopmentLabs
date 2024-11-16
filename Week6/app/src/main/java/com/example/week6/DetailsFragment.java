package com.example.week6;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.week6.Movie;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Movie movie;

    public DetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie",  movie);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie=getArguments().getParcelable("movie");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtName = (TextView)view.findViewById(R.id.txtMovieName);
        txtName.setText(movie.getName());
        TextView txtYear = (TextView)view.findViewById(R.id.txtYear);
        txtYear.setText(Integer.toString(movie.getYear()));
        TextView txtDirector = (TextView)view.findViewById(R.id.txtDirector);
        txtDirector.setText(movie.getDirector());
        TextView txtDescription = (EditText)view.findViewById(R.id.txtDescription);
        txtDescription.setText(movie.getDescription());
        txtDescription.setEnabled(false);
        ListView listView = (ListView) view.findViewById(R.id.lstStars);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.array_adapter,
                movie.getStars().toArray(new String[1])));
    }
    public void setMovie(View view, Movie movie) {
        this.movie = movie; // Update the fragment's Movie object

        // Update the views dynamically
        if (view != null) {
            TextView txtName = view.findViewById(R.id.txtMovieName);
            TextView txtYear = view.findViewById(R.id.txtYear);
            TextView txtDirector = view.findViewById(R.id.txtDirector);
            EditText txtDescription = view.findViewById(R.id.txtDescription);
            ListView lstStars = view.findViewById(R.id.lstStars);

            txtName.setText(movie.getName());
            txtYear.setText(String.valueOf(movie.getYear()));
            txtDirector.setText(movie.getDirector());
            txtDescription.setText(movie.getDescription());
            txtDescription.setEnabled(false);

            // Populate the list of stars
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    view.getContext(),
                    android.R.layout.simple_list_item_1,
                    movie.getStars()
            );
            lstStars.setAdapter(adapter);
        }
    }


}