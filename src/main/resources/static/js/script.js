

//Henrik Start
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('courseCalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        aspectRatio: 2,
        handleWindowResize: true,
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        hiddenDays: [0],
        weekNumbers: true,
        navLinks: true,
        nowIndicator: true,
        selectable: true,
        businessHours: {
            daysOfWeek: [1, 2, 3, 4, 5, 6], // Monday - Thursday
            startTime: '08:00', // a start time (10am in this example)
            endTime: '18:00', // an end time (6pm in this example)
        },

        events: 'http://localhost:8080/processCourse',

        // eventConstraint: {
        //     start: '2021-15-03',
        //     end: '2021-04-07', // hard coded goodness unfortunately
        //     //dow: [1, 2, 3 ,4 ,5]
        // },
        //Manuelle Events
        // [
        // {
        //     id: 1,
        //     title: 'Fallstudie',
        //     start: '2021-04-22T09:00:00',
        //     end: '2021-04-22T12:15:00',
        //     lecture: null,
        //     lecturer: null
        // }
        // ]

    });
    calendar.render();
});

// eventDataTransform: function(event) {
//     event.title = event.lecture;
//     return event;
// }



//Genau so wie bei Daniel - Es funktioniert!
// async function loadCourseEvents() {
//         url = "http://localhost:8080/processCourse";
//         const res = await fetch(url);
//         const json = await res.json();
//         console.log(json);
//         var calendarEl = document.getElementById('courseCalendar');
//         var calendar = new FullCalendar.Calendar(calendarEl, {
//             initialView: 'dayGridMonth',
//
//             headerToolbar: {
//                 left: 'prev,next today',
//                 center: 'title',
//                 right: 'dayGridMonth,timeGridWeek,timeGridDay'
//             },
//             hiddenDays: [0],
//             weekNumbers: true,
//             navLinks: true,
//             nowIndicator: true,
//             businessHours: {
//                 daysOfWeek: [1, 2, 3, 4, 5, 6], // Monday - Thursday
//                 startTime: '07:00', // a start time (10am in this example)
//                 endTime: '19:00', // an end time (6pm in this example)
//             },
//             dayMaxEvents: true,
//             editable: true,
//             events: json,
//         });
//         calendar.render();
// }