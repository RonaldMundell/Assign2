function CreateTable(Table){
    var tables = "";
    var i = 0;
    Table.forEach(rectangle => {
        if(i == 0){
           tables =+ "<tr>" 
        }
        tables =+ '<td><Div onclick="EditRectangle('+rectangle.getName+')" id="'+rectangle.getName()+'" background-color="'+rectangle.getBgcolor()+'" width='+rectangle.getWidth()+
        '" height='+rectangle.getHeight()+'"><p>'+rectangle.getName()+"</p><Div/></td>"
        i++;
        if(i == 3){
            tables =+ "</tr>"
            i = 0;
        }
    });
    var table = document.getElementById("rectangles")
    if(tables == ""){
    table.innerHTML = "No Rectangle created"
    }else{
    table.innerHTML = tables;
    }
}
function EditRectangle(name){
    
}