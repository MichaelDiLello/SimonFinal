package sjcny.simonfinal;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Arrays;
import sjcny.simonfinal.About;
import sjcny.simonfinal.R;

public class MainActivity extends AppCompatActivity {

    int flashed = 0;
    int score = 0;
    int counter = 0;
    int highScore = 0 ;
    int[] flashPattern;
    boolean mute = false;
    boolean status = false;

    Button redSquare;
    Button greenSquare;
    Button blueSquare;
    Button yellowSquare;

    EditText scoreView;
    EditText hiScore;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_about)
        {
            Intent myIntent = new Intent(this,About.class);
            startActivity(myIntent);

        }
        if (id == R.id.action_home)
        {
            System.exit(1);
        }

        if (id == R.id.action_mute)
        {
            if(mute == false)
            {
                Toast.makeText(MainActivity.this, "Sound Muted", Toast.LENGTH_LONG).show();
                mute = true;
            }
            else
            {
                Toast.makeText(MainActivity.this, "Sound Unmuted", Toast.LENGTH_LONG).show();
                mute = false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Squares
        redSquare = (Button) findViewById(R.id.redSquare);
        greenSquare = (Button) findViewById(R.id.greenSquare);
        yellowSquare = (Button) findViewById(R.id.yellowSquare);
        blueSquare = (Button) findViewById(R.id.blueSquare);

        scoreView =(EditText)findViewById(R.id.score);
        hiScore = (EditText)findViewById(R.id.highScore);

        Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                score = 0;
                counter = 0;
                scoreView.setText(String.valueOf(score));

                getPattern(score);
                final ColorChangingTask cct = new ColorChangingTask();
                cct.execute(flashPattern);
                status = true;

            }
        });

        redSquare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(status==true) {
                    if (checkPattern(1) == false) {
                        gameOver();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Press NEW GAME to Start", Toast.LENGTH_LONG).show();
                }
            }
        });

        greenSquare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(status==true)
                {
                    if (checkPattern(2) == false) {
                        gameOver();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Press NEW GAME to Start", Toast.LENGTH_LONG).show();
                }
            }
        });

        yellowSquare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(status==true) {
                    if (checkPattern(3) == false) {
                        gameOver();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Press NEW GAME to Start", Toast.LENGTH_LONG).show();
                }
            }
        });

        blueSquare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(status==true) {
                    if (checkPattern(4) == false) {
                        gameOver();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Press NEW GAME to Start", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void checkHighScore(int score)
    {
        if(score>highScore)
        {
            highScore = score;
            Toast.makeText(MainActivity.this, "New High Score!", Toast.LENGTH_LONG).show();
        }
    }


    public void getPattern(int score)
    {
        flashPattern = new int[score+1];

        for(int i = 0; i < flashPattern.length; i++)
        {
            flashPattern[i] = (int)(Math.random() * 4 + 1);
        }


    }

    public void gameOver()
    {
        Toast.makeText(MainActivity.this, "GAME OVER", Toast.LENGTH_LONG).show();

        if(mute == false)
        {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.loser);
            mp.start();
        }

        checkHighScore(score);
        hiScore.setText(String.valueOf(highScore));

        score = 0;
        scoreView.setText(String.valueOf(score));
        status = false;

    }

    public boolean checkPattern(int input)
    {

        if((flashPattern.length == 1))//if 1 element in array
        {
            if(flashPattern[counter] == input)//correct input
            {
                score++;
                scoreView.setText(String.valueOf(score));
                counter = 0;

                getPattern(score);
                ColorChangingTask cct = new ColorChangingTask();
                cct.execute();

                return true;
            }
            else //incorrect input
            {
                return false;
            }
        }
        else if(flashPattern.length > 1)//if >1 element in array
        {
            //if not at last element in array
            if((counter + 1) != flashPattern.length)
            {
                if(flashPattern[counter] == input)//correct input
                {
                    counter++;
                    return true;
                }
                else//incorrect input
                {
                    return false;
                }

            }
            else //if last element to check
            {
                if(flashPattern[counter] == input)//correct input
                {
                    score++;
                    scoreView.setText(String.valueOf(score));
                    counter=0;

                    getPattern(score);
                    ColorChangingTask cct = new ColorChangingTask();
                    cct.execute();

                    return true;
                }
                else//incorrect input
                {
                    return false;
                }
            }
        }
        else
            return false;

    }

    private class ColorChangingTask extends AsyncTask<int[], Integer, String>
    {
        @Override
        protected void onProgressUpdate(Integer... temp)
        {

            if (flashed == 1) {
                redSquare.setBackgroundColor(Color.BLACK);
            }
            if (flashed == 2) {
                greenSquare.setBackgroundColor(Color.BLACK);
            }
            if (flashed == 3) {
                yellowSquare.setBackgroundColor(Color.BLACK);
            }
            if (flashed == 4) {
                blueSquare.setBackgroundColor(Color.BLACK);
            }
            if (flashed == 5) {
                redSquare.setBackgroundColor(Color.RED);
            }
            if (flashed == 6) {
                greenSquare.setBackgroundColor(Color.GREEN);
            }
            if (flashed == 7) {
                yellowSquare.setBackgroundColor(Color.YELLOW);
            }
            if (flashed == 8) {
                blueSquare.setBackgroundColor(Color.BLUE);
            }


        }


        @Override
        protected String doInBackground(int[]... params)
        {

            for (int i = 0; i < flashPattern.length; i++) {
                try {

                    flashed = flashPattern[i];
                    publishProgress(flashed);
                    Thread.sleep(150);
                    flashed = flashPattern[i] + 4;
                    publishProgress(flashed);
                    Thread.sleep(300);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
    }



}
