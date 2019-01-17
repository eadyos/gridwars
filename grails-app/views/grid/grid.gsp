<%--
  Created by IntelliJ IDEA.
  User: steve
  Date: 1/9/15
  Time: 10:14 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <r:require modules="jquery"/>
    <r:layoutResources></r:layoutResources>
    <style>

    #playersWrapper{
        margin: 20px;
    }

    .playerName{
        padding: 4px;
        margin: 20px;
        color: white;
    }
    .score{
        display: inline-block;
        width: 20px;
    }

    .red{
        background-color: red;
    }
    .green{
        background-color: green;
    }
    .purple{
        background-color: purple;
    }
    .orange{
        background-color: orange;
    }
    .cyan{
        background-color: cyan;
        color: black;
    }
    .grey{
        background-color: gray;
    }
    .blue{
        background-color: blue;
    }
    .yellow{
        background-color: yellow;
        color: black;
    }
    .white{
        background-color: whitesmoke;
    }

    #gridWrapper{
        width: 95%;
        margin: auto;
        border-left: 1px solid #000;
        border-top: 1px solid #000;
    }
    .grid{
        width: 100%;
        border-spacing: 0px;
        padding: 0;
        border-collapse: collapse;
        display: none;
    }
    .grid td{
        border-right: 1px solid #000;
        border-bottom: 1px solid #000;
        padding: 0px;
        height: 14px;
    }

    #buttons{
        width: 100%;
        margin: 20px auto;
        text-align: center;
    }


    </style>
</head>

<body>

<div style="text-align: center">
    <g:img dir="images" file="gridwarsskull.jpg" width="150"></g:img>
</div>
<div style="float: right; margin-right: 35px;">
    <g:link action="index">Staging Area</g:link>
</div>

<div id="playersWrapper">

</div>

<div id="gridWrapper">
    <table id="grid" class="grid">
    </table>

    %{--<table id="grid1" class="grid">--}%
    %{--</table>--}%
</div>

<div id="buttons">
    <button id="beginButton">Begin War!</button>
    <button id="pauseButton">Pause</button>
</div>


<script>

    $(function(){

        var colors = ['blue', 'red', 'yellow', 'green', 'purple', 'orange', 'cyan', 'grey']
        var idColorMap = {0: 'white'};
        var finished = false;
        var turnCount = 0;
        var gridSize = 0;

        function populateGrid(data){
            $.each(data, function(index, value){
                var gridRow = $("<tr></tr>>");
                $.each(value, function(index2, unit){
                    var gridUnit = $("<td></td>");
                    gridUnit.addClass(idColorMap[unit]);
                    gridRow.append(gridUnit);
                    gridSize++;
                });
                $("#grid").append(gridRow);
            });
            $("#grid").show();
        }

        function updateGrid(data){
            var playerTotalsLocal = {};
            for (var key in idColorMap) {
                playerTotalsLocal[key] = 0
            }
            var gridRows = $('#grid tr');
            $.each(data, function(index, value){
                var gridUnits = $(gridRows[index]).find('td');
                $.each(value, function(index2, unit){
                    var gridUnit = gridUnits[index2];
                    $(gridUnit).removeClass().addClass(idColorMap[unit]);
                    var count = playerTotalsLocal[unit];
                    if(!count){
                        count = 1;
                        playerTotalsLocal[unit] = count;
                    }else{
                        playerTotalsLocal[unit] = ++count;
                    }
                });
            });
            return playerTotalsLocal;
        }

        function doTurns(){
            setTimeout(function(){
                turnCount++;

                $.getJSON("getGrid", function(data){
                    var totals = updateGrid(data);
                    for (var key in totals) {
                        if (totals.hasOwnProperty(key)) {
                            $('#player' + key).html("" + totals[key]);
                            if(totals[key] == gridSize){
                                finished = true;
                            }
                        }
                    }
                });

                if(!finished){
                    doTurns();
                }
            },${gridSpeed});
        }


        $.getJSON("getPlayers", function(data){

            $.each(data, function(index, value){
                idColorMap[value.id] = colors[index];
                $('#playersWrapper').append("<span class='playerName "  + colors[index] +"'>" + value.name +"</span> <span class='score' id='player" + value.id + "'>1</span>");
            });
            populateGrid(${gridData});
        });

        $('#beginButton').click(function(){
            finished = false;
            doTurns();
        });
        $('#pauseButton').click(function(){
            finished = true;
        });

    });


</script>

</body>
</html>