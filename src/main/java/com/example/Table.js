function CreateTable(Table){
    var tables = "";
    console.log(Table)
    Table.forEach(rectangle => {
       tables =+ "<tr><td>"+rectangle.id+"</td><td>"+rectangle.getName()
       +"</td><td>"+rectangle.getColor()+'</td><td><button value ="Edit Button" href="th:'
       +rectangle.getName()+'"></td></tr>';
    })
    document.getElementById("TheTable").append(tables)
}