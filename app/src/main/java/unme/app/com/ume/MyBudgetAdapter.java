package unme.app.com.ume;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import unme.app.com.ume.model.MyBudgetList;

public class MyBudgetAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyBudgetList> myBudgetLists;

    public MyBudgetAdapter(Context mContext, List<MyBudgetList> myBudgetLists) {
        this.mContext = mContext;
        this.myBudgetLists = myBudgetLists;
    }

    @Override
    public int getCount() {
        return myBudgetLists.size();
    }

    @Override
    public Object getItem(int position) {
        return myBudgetLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = View.inflate(mContext,R.layout.budget_list_item, null);
        TextView txtService = view.findViewById(R.id.txtService);
        TextView txtBudget = view.findViewById(R.id.txtBudget);
        TextView txtUserId = view.findViewById(R.id.txtUserID);
        TextView txtServiceId = view.findViewById(R.id.txtServiceID);

        txtService.setText(myBudgetLists.get(position).getService());
        txtBudget.setText(String.valueOf(myBudgetLists.get(position).getBudget())+" Rs");
        txtUserId.setText(myBudgetLists.get(position).getUserID());
        txtServiceId.setText(myBudgetLists.get(position).getServiceID());
        return view;
    }
}
