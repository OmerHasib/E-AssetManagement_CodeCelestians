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
                "Description":desc
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

    // Add an event listener to the back button to navigate to the homepage
    backButton.addEventListener('click', function () {
        window.location.href = 'homepage.html'; // Replace 'homepage.html' with the actual homepage URL
    });


    
    const productObject = JSON.parse(productString);
    const productOutput = document.getElementById('productOutput');
    productOutput.innerHTML = '<h2>Product JSON:</h2>';
    productOutput.innerHTML += '<pre>' + JSON.stringify(productObject, null, 2) + '</pre>';

    // function isValidDate(dateString) {
    //     // Check if the date string matches the dd/mm/yyyy format
    //     const dateRegex = /^\d{2}\/\d{2}\/\d{4}$/;
    //     return dateRegex.test(dateString);
    // }
});