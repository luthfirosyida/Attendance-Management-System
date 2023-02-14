$('#logoutButton').on('click', function(){
    Swal.fire({
        title: 'Logout',
        text: "Are you sure want to logout?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location = "/logout"
            
        }
    })
});

function showDataRegion() {
    window.location.replace("/region");
}

function showDataCountry() {
    window.location.replace("/country");
}

function showDataLocation() {
    window.location.replace("/location");
}

function showDataDepartment() {
    window.location.replace("/department");
}

function showDataEmployee() {
    window.location.replace("/employee");
}

function showDataJob() {
    window.location.replace("/job");
}

function showDataJobHistory() {
    window.location.replace("/jobhistory");
}
