package com.sbtso.BhajanViewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import android.widget.Button;
import android.view.View.OnClickListener;
/**
 * Created by sandeepperkari on 7/27/16.
 */
public class Bhajans  extends Fragment {
    List<BhajanXmlParser.Bhajan> bhajanList = null;
    private int bhajanItem = 0;
    private int bhajanListLength = 0;
    TextView headerTitle;
    TextView subHeaderTitle;
    TextView footerTitle;
    TextView subFooterTitle;
    TextView content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bhajans_fragment, container, false);
        headerTitle = (TextView)view.findViewById(R.id.header_title);
        subHeaderTitle = (TextView)view.findViewById(R.id.sub_header_title);
        footerTitle = (TextView)view.findViewById(R.id.footer_title);
        subFooterTitle = (TextView)view.findViewById(R.id.sub_footer_title);
        content = (TextView)view.findViewById(R.id.Bhajan_Text_Content);
        Button prev_button = (Button)view.findViewById(R.id.Button_Prev);

        prev_button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(bhajanItem > 0 ){
                    bhajanItem = bhajanItem - 1;
                    assignContentToView(bhajanItem);
                }
            }
        });

        Button next_button = (Button)view.findViewById(R.id.Button_Next);
        next_button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(bhajanItem <  bhajanListLength-1){
                    bhajanItem =  bhajanItem + 1;
                    assignContentToView(bhajanItem);
                }
            }
        });
        readDownloadedFile();
        return view;
    }
    public void readDownloadedFile(){
        String FILENAME = "hello_file";
        InputStream inputStream = null;
        try{
            File file = new File(getActivity().getApplicationContext().getFilesDir(),FILENAME);
            if(file != null){
                inputStream= new FileInputStream(file);
                BhajanXmlParser bhajanXmlParser = new BhajanXmlParser();
                bhajanList = bhajanXmlParser.parseBhajan(inputStream);
                bhajanListLength = bhajanList.size();
                assignContentToView(this.bhajanItem);
                //String xmlStringResp = processXmlResponse(inputStream);
                //  Log.e("File Read", xmlStringResp );
                // Toast.makeText(getActivity().getBaseContext(), xmlStringResp,Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }

    }
    public void assignContentToView(int item){
        if(bhajanList !=null && bhajanList.get(item)!= null){
            BhajanXmlParser.Bhajan bhajan =  bhajanList.get(item);
            if(bhajan !=null){
                headerTitle.setText(bhajan.bhajanName);
                subHeaderTitle.setText(bhajan.singerName);
                content.setText(bhajan.bhajanText);
                footerTitle.setText(bhajan.nextSinger);
                subFooterTitle.setText(bhajan.nextBhajan);
            }
        }
    }


}
