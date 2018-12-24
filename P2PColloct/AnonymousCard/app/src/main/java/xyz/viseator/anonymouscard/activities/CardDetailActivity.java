package xyz.viseator.anonymouscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.data.ConvertData;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.network.TcpClient;

public class CardDetailActivity extends AppCompatActivity {
    private static final String TAG = "wudi CardDetail";
    private TcpClient tcpClient;
    private DataPackage dataPackage;
    private UDPDataPackage receivedDataPackage;
    private ArrayList<DataPackage> dataPackages;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.img)
    ImageView imageView;
    @BindView(R.id.title)
    TextView title;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TcpClient.SERVER_PORT) {
                Log.d(TAG, "handleMessage: Receive Data");
                dataPackage = (DataPackage) msg.obj;
                content.setText(dataPackage.getContent());
                title.setText(dataPackage.getTitle());
                if (dataPackage.getBitmap() != null)
                    imageView.setImageBitmap(ConvertData.byteToBitmap(dataPackage.getBitmap()));
                Intent intent = new Intent();
                intent.putExtra("data", dataPackage);
                setResult(RESULT_OK, intent);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_content);
        ButterKnife.bind(this);
        dataPackages = MainActivity.dataPackages;
        receivedDataPackage = (UDPDataPackage) (getIntent().getSerializableExtra("data"));
        boolean contains = false;
        for (DataPackage dataPackage : dataPackages) {
            if (Objects.equals(receivedDataPackage.getId(), dataPackage.getId())) {
                contains = true;
                content.setText(dataPackage.getContent());
                title.setText(dataPackage.getTitle());
                if (dataPackage.getBitmap() != null)
                    imageView.setImageBitmap(ConvertData.byteToBitmap(dataPackage.getBitmap()));

                break;
            }
        }
        if (!contains) {
            tcpClient = new TcpClient();
            try {
                tcpClient.sendRequest(receivedDataPackage.getIpAddress(), receivedDataPackage, handler);
            } catch (NullPointerException e) {
                Toast.makeText(CardDetailActivity.this, "卡片不翼而飞了……", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }

}
