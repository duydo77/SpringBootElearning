var ctx = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['2014', '2015', '2016', '2017', '2018', '2019'],
        datasets: [{
            label: '# số khóa học',
            data: [120, 190, 100, 150, 220, 330],
            backgroundColor: ['rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'],
            borderColor: ['rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});

var ctx2 = document.getElementById('myChart2').getContext('2d');
var myChart2 = new Chart(ctx2, {
    type: 'line',
    data: {
        labels: ['2014', '2015', '2016', '2017', '2018', '2019'],
        datasets: [{
            label: '# số học viên',
            data: [1200, 1900, 1000, 1500, 2200, 3300],
            backgroundColor: ['rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'],
            borderColor: ['rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});