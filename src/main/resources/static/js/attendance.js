$('#table-attendance').DataTable({
    // order: [ 0, "desc"],
    "columnDefs": [
        // {"sortable":true, "visible":false, "target":0},
        {"sortable":true, "visible":false, "target":4},
        // {"sortable":true, "visible":false, "target":5}

    ],
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
            return ` <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#detailAttendance" onclick="findById(${data.id})">
            <i class="fa-solid fa-circle-info"></i>
            </button>
            `;
        }
    }]
})

function findById(id) {
    $.ajax({
        method: "GET",
        url: "/api/overtime/" + id,
        dataType: "JSON",
        success: result => {
            const starttime = new Date(result.start_time)
            const endtime = new Date(result.end_time)

            $("#overtime-id").text(`${result.id}`)
            $("#overtime-requester").text(`${result.requester.first_name}`+" "+`${result.requester.last_name}`)
            $("#overtime-start-time").text(`${starttime.toDateString()}, ${starttime.toLocaleTimeString()}`)
            $("#overtime-end-time").text(`${endtime.toDateString()}, ${endtime.toLocaleTimeString()}`)
            $("#overtime-total-hour").text(`${result.total_hour}`)
            $("#overtime-description").text(`${result.description}`)
            $("#overtime-project").text(`${result.project.name}`)
        }
    })
}

function findById(id) {
    $.ajax({
        method: "GET",
        url: "/api/attendance/" + id,
        dataType: "JSON",
        success: result => {

            const dateTemp = new Date(result.date)
            
            $("#attendance-id").text(`${result.id}`)
            $("#attendance-date").text(`${dateTemp.toDateString()} at ${dateTemp.toLocaleTimeString()}`)
            // $("#attendance-date").text(`${result.date.toDateString}`)
            $("#attendance-body-temperature").text(`${result.body_temperature}`)
            $("#attendance-workplace").text(`${result.workplace}`)
            $("#attendance-description").text(`${result.description}`)

            console.log(result);
        }
    })
}
