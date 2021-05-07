

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
            daysOfWeek: [1, 2, 3, 4, 5, 6],
            startTime: '08:00',
            endTime: '18:00',
        },

        events: 'http://localhost:8080/currentCourseEvents',

        eventMouseEnter: function(info) {
            info.el.style.cursor = "pointer";
        }

        // eventConstraint: {
        //     start: '2021-15-03',
        //     end: '2021-04-07', // hard coded goodness unfortunately
        //     //dow: [1, 2, 3 ,4 ,5]
        // },

    });
    calendar.render();
});

document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('tutorCalendar');
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
            daysOfWeek: [1, 2, 3, 4, 5, 6],
            startTime: '08:00',
            endTime: '18:00',
        },

        events: 'http://localhost:8080/currentTutorEvents',

        eventMouseEnter: function(info) {
            info.el.style.cursor = "pointer";
        }

        // eventConstraint: {
        //     start: '2021-15-03',
        //     end: '2021-04-07', // hard coded
        //     //dow: [1, 2, 3 ,4 ,5]
        // },

    });
    calendar.render();
});



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