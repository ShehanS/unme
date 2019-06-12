package unme.app.com.ume;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import unme.app.com.ume.model.RequestClientList;

public class RequestClientAdapter extends BaseAdapter {
    private Context mContext;
    private List<RequestClientList> mClientList;

    public RequestClientAdapter(Context mContext, List<RequestClientList> mClientList) {
        this.mContext = mContext;
        this.mClientList = mClientList;
    }

    @Override
    public int getCount() {
        return mClientList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = View.inflate(mContext, R.layout.request_list,null);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtCategory = view.findViewById(R.id.txtCategory);
        TextView txtUserID = view.findViewById(R.id.txtUserID);
        TextView txtContact = view.findViewById(R.id.txtContact);
        TextView txtServiceId = view.findViewById(R.id.txtServiceID);


        txtName.setText(mClientList.get(position).getName());
        txtCategory.setText(mClientList.get(position).getService());
        txtUserID.setText(mClientList.get(position).getUserID());
        txtServiceId.setText(mClientList.get(position).getServiceID());
        txtContact.setText(mClientList.get(position).getContact());


        return view;
    }
}
