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
        .description{
            width: 700px;
            margin: 20px auto;
        }
        p{

        }
        .iWrap{
            clear: both;
            margin-top: 30px;
            height: 100px;
            border-bottom: 1px solid #000;
        }
        .wrap{
            clear: both;
            margin-top: 30px;
            border-bottom: 1px solid #000;
        }
        .iWrap span{
            width: 140px;
            float:left;
            margin-left: 12px;
        }
        .wrap span{
            width: 100px;
            float:left;
            margin-left: 20px;
        }
        .wrap span.wider{
            width: 180px;
            margin-left: 10px;
        }
        .summary{
            width: 450px;
            float: left;
        }
        .links{
            font-weight: bold;
            margin-left: 60px;
            margin-top: 25px;
            width: 150px;
            float: left;
        }
    </style>
</head>

<body>

<div style="text-align: center">
    <g:img dir="images" file="gridwarsskull.jpg" width="500"></g:img>
</div>

<div class="description">
    <div class="summary">
        <p>Grid Wars is a programming competition where programs are run within cells in a grid.</p>
        <p>Each cell that you control runs a copy of your program.</p>
        <p>Each round, your program shoots bullets into neighboring cells in an attempt to take control of those cells.</p>
        <p>To win, your program must take control of the entire grid, or control the most number of cells if a gridlock
        has occurred.</p>
    </div>
    <div class="links">
        <g:link controller="grid" action="index">Login</g:link>
    </div>
    <div style="clear: both;">
    </div>

    <div class="iWrap">
        <span>
            <g:img dir="images" file="instruct1a.png"/>
        </span>
        <span>
            Program is seeded into a starting cell
        </span>
        <span>
            <g:img dir="images" file="instruct1b.png"/>
        </span>
        <span>
            Program shoots bullets into adjacent cell
        </span>

    </div>
    <div class="iWrap">
        <span>
            <g:img dir="images" file="instruct2.png"/>
        </span>
        <span>
            Program now controls 2 cells
        </span>
        <span>
            <g:img dir="images" file="instruct2b.png"/>
        </span>
        <span>
            Each cell shoots bullets into adjacent cells
        </span>
    </div>
    <div class="iWrap">
        <span>
            <g:img dir="images" file="instruct3.png"/>
        </span>
        <span>
            Program now controls 4 cells.
        </span>
    </div>

    <div>
        <p>
            The programs are simple Groovy closures that most anyone should be able to create.
        </p>
        <p>
            The closures will receive input indicating the state of the adjacent cells.
        </p>
        <p>
            The closures will return output indicating which cells to shoot bullets into.
        </p>

        <div class="wrap">
            <span>
                <g:img dir="images" file="input1.png"/>
            </span>
            <span class="wider">
                The blue cell is surrounded by 3 kinds of spaces.  Empty, Friendly, and Enemy.
            </span>
            <span>
                <g:img dir="images" file="input2.png"/>
            </span>
            <span class="wider">
                Cell types get translated into numerical values, and passed into the program.
            </span>
            <div style="clear: both"></div>
            <p>
                Adjacent cell states are passed in as an array
            </p>
            <p style="font-family: monospace">
                [upperLeft, upper, upperRight, left, right, lowerLeft, lower, lowerRight]
            </p>
            <p style="font-family: monospace">
                [2, 2, 0, 0, 2, 1, 1, 0]
            </p>
            <p>
                Bullets fired are passed out as an array.  In this example, we shoot 3 bullets into the lowerRight/empty cell.
            </p>
            <p style="font-family: monospace">
                [0, 0, 0, 0, 0, 0, 0, 3]
            </p>
        </div>
    </div>

    <div class="wrap">
        <p>Example Program</p>
        <p style="font-family: monospace;">
            { int[] input -><br/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;input[0] //upperLeft<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;input[4] //left<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;input[8] //lowerRight<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;//etc.<br/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;return [0,3,0,0,0,0,0,0] //shoot 'em up!<br/>
            }
        </p>
    </div>

    <div class="wrap">
        <p>Rules:</p>

        <p>Programs can only return a maximum of 3 bullets.  If the program returns more than 3 bullets, it's bullets
        will be disregarded.</p>

        <p>
            The program with the most number of bullets in a cell takes over the cell
        </p>

        <p>
            It takes at least 3 bullets entering a cell to take it over.
        </p>

        <p>
            If 2 different programs have an equal number of bullets in a cell, they cancel each other out
            and the owner of the cell will not change.
        </p>

        <p>By logging in, you will have access to a test harness.  You can upload your closures or copy/paste them
        into the system.</p>

        <p>You can create as many closures/entries as you want.</p>

        <p>
            During the competition, 6 programs will be run at a time.
        </p>
        <p>The grid is 50x50 for a total of 2500 cells.</p>

        <p>The grid wraps.  The cells on the right and left connect with each other.
        The cells on the top and bottom connect with each other.</p>
    </div>

</div>

</body>
</html>