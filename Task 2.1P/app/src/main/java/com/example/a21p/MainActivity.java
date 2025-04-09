package com.example.a21p;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    EditText inputBox;
    Button convertButton;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = findViewById(R.id.spinnerSourceUnit);
        toSpinner = findViewById(R.id.spinnerDestinationUnit);
        inputBox = findViewById(R.id.editTextValue);
        convertButton = findViewById(R.id.buttonConvert);
        resultText = findViewById(R.id.textViewResult);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.units_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();
                String inputValue = inputBox.getText().toString();

                if (inputValue.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double numberToConvert;

                try {
                    numberToConvert = Double.parseDouble(inputValue);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Not a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (fromUnit.equals(toUnit)) {
                    Toast.makeText(MainActivity.this, "Choose different units", Toast.LENGTH_SHORT).show();
                    return;
                }


                double answer = convertIt(fromUnit, toUnit, numberToConvert);

                if (answer == Double.MIN_VALUE) {
                    Toast.makeText(MainActivity.this, "Conversion not supported", Toast.LENGTH_SHORT).show();
                } else {
                    resultText.setText("Converted Value: " + answer);
                }
            }
        });
    }

    private double convertIt(String from, String to, double value) {
        // Length conversions
        if (from.equals("Inches") && to.equals("Centimeters")) {
            return value * 2.54;
        } else if (from.equals("Feet") && to.equals("Centimeters")) {
            return value * 30.48;
        } else if (from.equals("Yards") && to.equals("Centimeters")) {
            return value * 91.44;
        } else if (from.equals("Miles") && to.equals("Kilometers")) {
            return value * 1.60934;
        }

        else if (from.equals("Pounds") && to.equals("Kilograms")) {
            return value * 0.453592;
        } else if (from.equals("Ounces") && to.equals("Grams")) {
            return value * 28.3495;
        } else if (from.equals("Tons") && to.equals("Kilograms")) {
            return value * 907.185;
        }

        else if (from.equals("Celsius") && to.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        } else if (from.equals("Fahrenheit") && to.equals("Celsius")) {
            return (value - 32) / 1.8;
        } else if (from.equals("Celsius") && to.equals("Kelvin")) {
            return value + 273.15;
        } else if (from.equals("Kelvin") && to.equals("Celsius")) {
            return value - 273.15;
        }

        return Double.MIN_VALUE;
    }
}
