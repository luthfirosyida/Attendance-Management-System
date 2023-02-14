$('#table-attendance').DataTable({
    ajax: {
        url: '/api/attendance',
        dataSrc: ''
    },
    columns: [{
        data: null,
        render : (data, type, row, meta) => {
            return meta.row + 1
        }
    }, {
        data: null,
        render: function (data, type, row, meta) {
            const date = new Date(data.date);
            return `${date.toDateString()}`;
        }
    }, {
        data: 'body_temperature'
    },{
        data:'workplace'
    },{
        data: 'description'
    },{
        data: null,
        render: function (data, type, row, meta) {
            return ` <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#detailProject" onclick="findById(${data.id})">
            <i class="fa-solid fa-circle-info"></i>
            </button>
            `;
        }
    }]
})

function findById(id) {
    $.ajax({
        method: "GET",
        url: "/api/attendance/" + id,
        dataType: "JSON",
        success: result => {

            // let resultProjectVal = rupiah(result.project_value)
            
            $("#attendance-id").text(`${result.id}`)
            $("#attendance-date").text(`${result.date}`)
            $("#attendance-body-temperature").text(`${result.body_temperature}`)
            $("#attendance-workplace").text(`${result.workplace}`)
            $("#attendance-description").text(`${result.description}`)
           

            console.log(result);
        }
    })
}
