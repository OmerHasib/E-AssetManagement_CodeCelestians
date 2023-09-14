document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('vaccinationForm');
    const validateButton = document.getElementById('validateButton');
    const resetButton = document.getElementById('resetButton');
    const outputDiv = document.getElementById('output');
    const backButton = document.getElementById('backButton'); // Add this line

    validateButton.addEventListener('click', function () {
        const name = form.elements.name.value;
        const vaccine = form.elements.vaccine.value;
        const acknowledgement = form.elements.acknowledgement.checked;
        const desc = form.elements.desc.value;

        if (!name || !vaccine || !acknowledgement) {
            alert("Invalid entry. Please fill out all fields.");
        } else {
            const formData = {
                "Name": name,
                "Asset added": vaccine,
                "Acknowledgement Accepted": acknowledgement,
                "Unique Id":desc
            };

            console.log(JSON.stringify(formData, null, 2));
            // Clear previous output before adding new data
            outputDiv.innerHTML = '<pre>' + JSON.stringify(formData, null, 2) + '</pre>';
            alert("Asset added successfully!");
        }
    });

    resetButton.addEventListener('click', function () {
        form.reset();
        outputDiv.innerHTML = ''; // Clear the displayed data
    });

    

    
    const productObject = JSON.parse(productString);
    const productOutput = document.getElementById('productOutput');
    productOutput.innerHTML = '<h2>Product JSON:</h2>';
    productOutput.innerHTML += '<pre>' + JSON.stringify(productObject, null, 2) + '</pre>';

   
});