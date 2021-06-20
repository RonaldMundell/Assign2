function createTable(Table){
    var tables = "";
    console.log("Hello")
    Table.forEach(rectangle => {
       tables =+ "<tr><td>"+rectangle.id+"</td><td>"+rectangle.getName()
       +"</td><td>"+rectangle.getColor()+'</td><td><button value ="Edit Button" href="th:'
       +rectangle.getName()+'"></td></tr>';
    })
    document.getElementById("TheTable").append(tables)
}