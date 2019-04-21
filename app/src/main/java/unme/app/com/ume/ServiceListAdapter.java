package unme.app.com.ume;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import unme.app.com.ume.model.ClientService;

public class ServiceListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ClientService> mServiceList;


    public ServiceListAdapter(Context mContext, List<ClientService> mServiceList) {
        this.mContext = mContext;
        this.mServiceList = mServiceList;
    }

    @Override
    public int getCount() {
        return mServiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return mServiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = View.inflate(mContext, R.layout.list_item,null);
        TextView txtCompany = (TextView) view.findViewById(R.id.txtCompany);
        TextView txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        TextView txtRate = (TextView) view.findViewById(R.id.txtRate);
        TextView txtServiceID = (TextView) view.findViewById(R.id.txtServiceID);

        txtCompany.setText("Company : "+mServiceList.get(position).getCompany());
        txtCategory.setText("Category : "+mServiceList.get(position).getCategory());
        txtRate.setText("Rating : "+String.valueOf(mServiceList.get(position).getRate()));
        txtServiceID.setText("Service id : "+mServiceList.get(position).getServiceID());
        view.setTag(mServiceList.get(mServiceList.get(position).getId()));

        return view;
    }
}
