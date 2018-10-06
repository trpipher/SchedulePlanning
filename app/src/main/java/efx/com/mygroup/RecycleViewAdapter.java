package efx.com.mygroup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.mViewHolder> {

    //RecycleView Variables

    //Array that will contain the information from the dataset, the info to be displayed on individual fragments
    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();

    //Context is basically the system getting the current state of an object/environment
    //Simply put: Each list item has its own individual context
    private Context context;

    //Adapter Constructor [Connects DataSet to ArrayLists]
    //'d' prefix represents "data"
    public RecycleViewAdapter(ArrayList<String> dHours, ArrayList<String> dTimes, Context dContext){
        hours = dHours;
        times = dTimes;
        context = dContext;
    }


    //Note: Override methods are default methods of the AndroidOS that we are modifying to suit our needs

    @NonNull
    @Override
    //Method responsible for inflating the view
    //Inflating - To load and render a resource then hold it in memory for quick access
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_blank, parent, false);
        mViewHolder holder = new mViewHolder(mView);
        return holder;
    }

    @Override
    //This method is called every single time a new item is added to the list
    //Allows you to modify template items [such as fragments] as they are created in the list
    //This process will be fairly automatic since we are typically creating these objects from existing data
    public void onBindViewHolder(@NonNull mViewHolder holder, final int position) {
        //Log.i(, )
        holder.fragHour.setText(hours.get(position));
        holder.fragTime.setText(times.get(position));
        
        holder.fragmentParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked at:", hours.get(position));
                Toast.makeText(context, hours.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    //Returns the total number of existing items by returning the size of the ArrayList
    public int getItemCount() {
        return hours.size();
    }


    //Holds individual recyclerview elements in memory, for faster/efficient loading
    //By putting the fragment into the memory findViewById(...), which is expensive,
    //Only has to be called once, instead of everytime an object is creaated
        //IE: References the fragment template, puts it in memory for fast access
            //Process is automated by the AndroidOS
    public class mViewHolder extends RecyclerView.ViewHolder{

        //Variables
        TextView fragHour;
        TextView fragTime;
        ConstraintLayout fragmentParent;

        public mViewHolder(View itemView){
            super(itemView);
            fragHour = itemView.findViewById(R.id.hourOfDay);
            fragTime = itemView.findViewById(R.id.fragTime);
            fragmentParent = itemView.findViewById(R.id.userFragment);

        }
    }

}
