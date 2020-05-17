package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Parceptron extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parceptron);
        final int P = 4;
        final int[][] pointsArr = {{0, 6}, {1, 5}, {3, 3}, {2, 4}};

        TextView points = findViewById(R.id.points);
        StringBuilder arr = new StringBuilder("P=" + P + "\n");
        for (int[] ints : pointsArr) {
            arr.append("(");
            arr.append(ints[0]).append("; ");
            arr.append(ints[1]).append("), ");
        }
        points.setText(arr.toString());

        Button calc = findViewById(R.id.button);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView edTime = findViewById(R.id.edTime);
                int t = Integer.parseInt(edTime.getText().toString());
                int iter = Integer.parseInt(((TextView) findViewById(R.id.edIter)).getText().toString());
                double sigma = Double.parseDouble(((TextView) findViewById(R.id.edSigma)).getText().toString());
                double[] result = parceptron(P, pointsArr, sigma, t, iter);
                String res;
                res = "w1=" + result[0] + "\n";
                res += "w2=" + result[1] + "\n";
                res += "Количество итераций: " + result[2] + "\n";
                res += "Время: " + result[3] + "\n";
                ((TextView) findViewById(R.id.res)).setText(res);
            }
        });
    }

    public static double[] parceptron(int P, int[][] points, double sigma, long time, int iter) {
        double delta;
        double y;
        double w1 = 0, w2 = 0;

        int j = 0;
        long timeBefore = System.currentTimeMillis();
        long timeAfter = 0;
        for (int k = 0; k < iter; k++) {
            {
                for (int[] point : points) {
                    j++;
                    y = point[0] * w1 + point[1] * w2;
                    if (y == Double.NEGATIVE_INFINITY || y == Double.POSITIVE_INFINITY) {
                        System.out.println();
                    }
                    delta = P - y;
                    w1 = w1 + delta * sigma * point[0];
                    w2 = w2 + delta * sigma * point[1];
                    timeAfter = System.currentTimeMillis();
                    if (w1 == Double.NEGATIVE_INFINITY) {
                        System.out.println();
                    }
                    if ((w1 * points[0][0] + w2 * points[0][1] > P &&
                            w1 * points[1][0] + w2 * points[1][1] > P &&
                            w1 * points[2][0] + w2 * points[2][1] < P &&
                            w1 * points[3][0] + w2 * points[3][1] < P) ||
                            timeAfter - timeBefore > time) {
                        return new double[]{w1, w2, j, timeAfter - timeBefore};
                    }
                }
            }
        }
        return new double[]{w1, w2, j, timeAfter - timeBefore};
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Parceptron.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception ignored) {
        }
    }
}
