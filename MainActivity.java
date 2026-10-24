package demo.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private SharedPreferences prefs;
    private long balance;
    private TextView balanceView;

    private static final long INITIAL_BALANCE = 500000000000L;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        prefs = getSharedPreferences("demo_wallet", MODE_PRIVATE);
        balance = prefs.getLong("balance", INITIAL_BALANCE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);
        layout.setBackgroundColor(Color.rgb(20, 30, 45));

        TextView title = new TextView(this);
        title.setText("DEMO WALLET");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        balanceView = new TextView(this);
        balanceView.setTextColor(Color.rgb(100, 255, 160));
        balanceView.setTextSize(22);
        balanceView.setGravity(Gravity.CENTER);
        balanceView.setPadding(0, 32, 0, 32);
        layout.addView(balanceView);

        addButton(layout, "Add 100 Coins", 1);
        addButton(layout, "Spend 25 Coins", 2);
        addButton(layout, "Reset Balance", 3);

        TextView note = new TextView(this);
        note.setText("Local demo only - no real money");
        note.setTextColor(Color.LTGRAY);
        note.setGravity(Gravity.CENTER);
        note.setPadding(0, 30, 0, 0);
        layout.addView(note);

        setContentView(layout);
        refresh();
    }

    private void addButton(LinearLayout layout, String label, int action) {
        Button button = new Button(this);
        button.setText(label);
        layout.addView(button);

        final int selectedAction = action;
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (selectedAction == 1) {
                    balance += 100;
                } else if (selectedAction == 2 && balance >= 25) {
                    balance -= 25;
                } else if (selectedAction == 3) {
                    balance = INITIAL_BALANCE;
                }

                prefs.edit().putLong("balance", balance).apply();
                refresh();
            }
        });
    }

    private void refresh() {
        balanceView.setText("Coins: " + balance);
    }
}
