// Undo Stack
let historyStack = [];

// Function to perform fraction calculation
function calculate(operation) {
    const numerator1 = parseInt(document.getElementById('numerator1').value);
    const denominator1 = parseInt(document.getElementById('denominator1').value);
    const numerator2 = parseInt(document.getElementById('numerator2').value);
    const denominator2 = parseInt(document.getElementById('denominator2').value);

    if (denominator1 === 0 || denominator2 === 0 || denominator1 == 0 && denominator2 == 0) {
        alert("Denominator cannot be zero!");
        return;
    }

    let resultNumerator = 0;
    let resultDenominator = 0;

    switch (operation) {
        case "+":
            resultNumerator = numerator1 * denominator2 + numerator2 * denominator1;
            resultDenominator = denominator1 * denominator2;
            break;
        case "-":
            resultNumerator = numerator1 * denominator2 - numerator2 * denominator1;
            resultDenominator = denominator1 * denominator2;
            break;
        case "*":
            resultNumerator = numerator1 * numerator2;
            resultDenominator = denominator1 * denominator2;
            break;
        case "/":
            resultNumerator = numerator1 * denominator2;
            resultDenominator = denominator1 * numerator2;
            if (resultDenominator === 0) {
                alert("Division by zero error!");
                return;
            }
            break;
    }

    // Save the current result in the history stack
    historyStack.push(document.getElementById('result').value);

    // Display the result
    document.getElementById('result').value = `${numerator1}/${denominator1} ${operation} ${numerator2}/${denominator2} = ${resultNumerator}/${resultDenominator}`;
}

// Clear the input fields
function clearFields() {
    document.getElementById('numerator1').value = '';
    document.getElementById('denominator1').value = '';
    document.getElementById('numerator2').value = '';
    document.getElementById('denominator2').value = '';
}

// Reset the calculator
function resetCalculator() {
    clearFields();
    document.getElementById('result').value = 'Ergebnis wird hier angezeigt...';
}

// Undo the last calculation
function undo() {
    if (historyStack.length > 0) {
        document.getElementById('result').value = historyStack.pop();
    } else {
        alert("Nothing to undo!");
    }
}
// Funktion zum Öffnen der PDF-Dokumentation
function openDocumentation() {
    window.open('docs/dokumentation.pdf', '_blank');
}

