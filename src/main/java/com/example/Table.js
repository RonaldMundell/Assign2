
function createTable(Table){
    var tables = "<tr><td>Enter Data</td></tr>";
    var i = 0;
    Table.forEach(rectangle => {
        if(i == 0 ){
            tables = "";
            i = 2;
        };
       tables =+ "<tr><td>"+rectangle.id+"</td><td>"+rectangle.getName()
       +"</td><td>"+rectangle.getColor()+'</td><td><button value ="Edit Button" href="th:'
       +rectangle.getName()+'"></td></tr>';
    })
    document.getElementById("TheTable").append(tables)
}