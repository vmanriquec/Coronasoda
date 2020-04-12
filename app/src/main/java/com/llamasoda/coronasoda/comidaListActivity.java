package com.llamasoda.coronasoda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.llamasoda.coronasoda.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.llamasoda.coronasoda.dummy.DummyContent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a list of comida. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link comidaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class comidaListActivity extends AppCompatActivity {
    private static final String PATH_FOOD = "food";
    private static final String PATH_PROFILE = "profile";
    private static final String PATH_CODE = "code";
    @BindView(R.id.etname)
    EditText etname;
    @BindView(R.id.etprice)
    EditText etprice;
    @BindView(R.id.btnsave)
    Button btnsave;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        Button u = (Button) findViewById(R.id.btnsave);
        TextView d = (TextView) findViewById(R.id.etname);
        TextView o = (TextView) findViewById(R.id.etprice);

        u.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(comidaListActivity.this, "entroooooo", Toast.LENGTH_LONG).show();

                DummyContent.Comida comida = new DummyContent.Comida(d.getText().toString().trim(), o.getText().toString().trim());
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference reference = database1.getReference(PATH_FOOD);
//comprar el nomnre guardado en el objeto comida lo compara
// con getcomida y retorna un comida si es null enton graba normal
                //si no update
                DummyContent.Comida comidaupdate= DummyContent.getComida(comida.getNombre());


               if(comidaupdate!=null){
                   reference.child(comidaupdate.getId()).setValue(comida);
                   d.setText("");
                   o.setText("");

               }
               else {


                   reference.push().setValue(comida);
                   d.setText("");
                   o.setText("");
               }
            }

        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.comida_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.comida_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_FOOD);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DummyContent.Comida comida = dataSnapshot.getValue(DummyContent.Comida.class);
                comida.setId(dataSnapshot.getKey());
                if (!DummyContent.ITEMS.contains(comida)) {
                    DummyContent.addItem(comida);
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                DummyContent.Comida comida = dataSnapshot.getValue(DummyContent.Comida.class);
                comida.setId(dataSnapshot.getKey());
                if (DummyContent.ITEMS.contains(comida)) {
                    DummyContent.updateitem(comida);
                }

                recyclerView.getAdapter().notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                DummyContent.Comida comida = dataSnapshot.getValue(DummyContent.Comida.class);
                comida.setId(dataSnapshot.getKey());
                if (DummyContent.ITEMS.contains(comida)) {
                    DummyContent.deleteitem(comida);
                }

                recyclerView.getAdapter().notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
////activacion de menu en la esquina
    /////
    ///////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
/////menuuuu opcionessss menuuuuuu//////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
switch (item.getItemId()){
    case R.id.action_info:

        //fijando y creando parametros a tecxtview dinamicamente
final TextView tvcode=new TextView(this);

        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
tvcode.setLayoutParams(params);
tvcode.setGravity(Gravity.CENTER_HORIZONTAL);
tvcode.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
///////////////////////////////////////////////////


FirebaseDatabase database=FirebaseDatabase.getInstance();
DatabaseReference reference=database.getReference(PATH_PROFILE).child(PATH_CODE);
reference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
     tvcode.setText(dataSnapshot.getValue(String.class));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
Toast.makeText(comidaListActivity.this,"no se puede cargar el codigo",Toast.LENGTH_LONG).show();



    }
});
        AlertDialog.Builder builder= new AlertDialog.Builder(this)
                .setTitle(R.string.comidalist_dialog_title)
                .setPositiveButton(R.string.comidalist_dialog_ok,null);
builder.setView(tvcode);
builder.show();
        break;


}
        return  super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final comidaListActivity mParentActivity;
        private final List<DummyContent.Comida> mValues;
        private final boolean mTwoPane;



        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.Comida item = (DummyContent.Comida) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(comidaDetailFragment.ARG_ITEM_ID, item.getId());
                    comidaDetailFragment fragment = new comidaDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.comida_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, comidaDetailActivity.class);
                    intent.putExtra(comidaDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };
        @BindView(R.id.btndelete)
        Button btndelete;

        SimpleItemRecyclerViewAdapter(comidaListActivity parent,
                                      List<DummyContent.Comida> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comida_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText("S/." + mValues.get(position).getPrecio());
            holder.mContentView.setText(mValues.get(position).getNombre());
holder.btndelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference(PATH_FOOD);
        reference.child(mValues.get(position).getId()).removeValue();
    }
});
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @OnClick(R.id.btndelete)
        public void onClick() {
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView, mPrecio;
@BindView(R.id.btndelete)
        Button btndelete;


            ViewHolder(View view) {
                super(view);
                ButterKnife.bind( this,view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.nombre);
                mPrecio = (TextView) view.findViewById(R.id.etprice);
            }
        }
    }
}
