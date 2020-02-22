const dataChartOptions = {
    hAxis: {title: 'Amount Hours'},
    vAxis: {title: 'Name'},
    bars: 'horizontal',
    width: '100%',
    height: 800
};

let chartRef;

//consider combining both requests into one
//advantages: 1 http call, no need for timeout
window.onload = function () {
    //no need for timeout since the response is arriving post dbInit procedure completion
    performHttpRq('GET', 'init', processUserData);

    //the dbinit procedure is not finished yet, set Timeout
    setTimeout(function () {
        performHttpRq('GET', 'chart/data/users' + prepFromToDateFilterRqParams(), updateDataChartViewUsers);
    }, 1000);
};


function processUserData(userJsonData) {
    //parse json
    const userData = userJsonData;
    //show table
    initializeTable(userData);
    //set up DataTable library
    initializeDataTable(userData);
}


/**
 * Create table on the left side of the page
 * Add thead and tbody, populate
 */
function initializeTable(userData) {
    const table = document.getElementById('userTable');
    //tbody
    userData.forEach(u => createCell(table.insertRow(), [u.id, u.name, u.surname, u.email]));
    //thead (added after tbody because otherwise every user row is added to thead and DataTable() doesn't work)
    createCell(table.createTHead().insertRow(), ["ID", "First Name", "Last Name", "Email"]);

    function createCell(row, text) {
        text.forEach(str => row.insertCell().textContent = str);
    }
}


/**
 * Set up DataTables library
 * Point it to the table created on the left side of the page
 * Set up server-side ordering and pagination, drawCallBack etc..
 */
function initializeDataTable() {

    const tableRef = $('#userTable').DataTable(
        {
            "bProcessing": true,
            "bServerSide": true, //server side enabled
            "sServerMethod": "POST", // http method
            "sAjaxSource": "user/paginated", //url
            "columns": [ //server response columns to parse
                {"data": "id", "name": "id"},
                {"data": "name", "name": "name"},
                {"data": "surname", "name": "surname"},
                {"data": "email", "name": "email"}
            ],
            //override request to server
            "fnServerData": function (sSource, aoData, fnCallback, oSettings) {
                oSettings.jqXHR = $.ajax({
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": prepareRqBody(aoData), //custom rq body
                    "contentType": "application/json",
                    "success": fnCallback
                });
            }
        }
    );
    tableRef.on('click', 'tbody > tr', function () {
        const userId = JSON.parse(JSON.stringify(tableRef.row(this).data())).id;
        document.getElementById('userChecked').checked ?
            performHttpRq('GET', 'chart/data/users/' + userId + prepFromToDateFilterRqParams(), updateDataChartViewUsers) :
            performHttpRq('GET', 'chart/data/projects/' + userId + prepFromToDateFilterRqParams(), updateDataChartViewProjects);
    });
}


/**
 * Prepare Request Body for Back-End pagination
 * @param aoData all the data that DataTables lib sends to BE
 * @returns {string} only the data necessary to BE to perform pagination and ordering
 */
function prepareRqBody(aoData) {
    const jsonDataArray = JSON.parse(JSON.stringify(aoData));

    const jsonData = {};
    jsonData.sEcho = jsonDataArray[0].value;
    jsonData.firstRowId = jsonDataArray[3].value; //iDisplayLength
    jsonData.rowsPerPage = jsonDataArray[4].value; //iDisplayStart
    jsonData.sortCol = jsonDataArray[27].value; //iSortCol_0 index col to order by

    return JSON.stringify(jsonData);
}


/**
 * Reload Bar Chart view with new user data
 */
function updateDataChartViewUsers(data) {
    const dataTable = [["", "", {role: 'style'}]];
    data.forEach(u => dataTable.push([u.user_id + '. ' + u.full_name, u.time, u.compared ? 'red' : 'blue']));
    chartRef = new google.visualization.BarChart(document.getElementById('chart'));
    chartRef.draw(google.visualization.arrayToDataTable(dataTable), google.charts.Bar.convertOptions(dataChartOptions));
}

/**
 * Reload Bar Chart view with new project data
 */
function updateDataChartViewProjects(data) {
    const dataTable = [["", "", {role: 'style'}]];
    data.forEach(u => dataTable.push([u.user_id + '.' + u.full_name + ',' + u.project_id + '.' + u.project_name, u.time, u.compared ? 'red' : 'blue']));
    chartRef = new google.visualization.BarChart(document.getElementById('chart'));
    chartRef.draw(google.visualization.arrayToDataTable(dataTable), google.charts.Bar.convertOptions(dataChartOptions));
}


function radioBtnClick(data) {
    switch (data) {
        case 'project':
            performHttpRq('GET', 'chart/data/projects' + prepFromToDateFilterRqParams(), updateDataChartViewProjects);
            break;
        case 'user':
            performHttpRq('GET', 'chart/data/users' + prepFromToDateFilterRqParams(), updateDataChartViewUsers);
    }
}

function prepFromToDateFilterRqParams() {
    const from = document.getElementById('fromDateFilter').value;
    const to = document.getElementById('toDateFilter').value;
    return '?from=' + from + '&to=' + to;
}


function performHttpRq(httpVerb, url, fnCallback) {
    const http = new XMLHttpRequest();
    http.open(httpVerb, url, true);
    http.send();
    http.onload = function () {
        if (http.readyState === 4) {
            http.status === 200 ? fnCallback(JSON.parse(http.responseText)) : console.error(http.statusText);
        }
    };
}


/**
 * Reload page
 */
function reload() {
    location.reload();
}
