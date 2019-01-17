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

    .userWrapper{
        margin-bottom: 12px;
    }
    .userName{
        font-weight: bold;
        margin-bottom: 6px;
    }
    #addAlgoWrapper{
        margin: 20px 0px;
    }
    #fileUpload{
        margin: 12px 0px;
    }
    #textUpload{
        margin: 12px 0px;
    }
    .deleteAlgo{
        font-size: x-small;
    }
    .wrapper{
        width: 90%;
        margin: auto;
        padding-top: 30px;

    }
    .entries{
        float:left;
        width: 300px;
    }
    .uploads{
        float:left;
        width: 400px;
        margin-left: 50px;
    }
    #clearSelections{
        margin-bottom: 10px;
        font-size: smaller;
    }
    </style>
</head>

<body>
<g:message>test</g:message>

<div style="text-align: center">
    <g:img dir="images" file="gridwarsskull.jpg" width="400"></g:img>
</div>


<div class="wrapper">
    <div class="entries">
        <h3>Select the program participants (Max 6)</h3>
        <div id="participantSelectionWrapper">
            <g:form name="participantForm" action="createGrid">
                <g:each in="${users}" var="user">
                    <g:if test="${user.algos}">
                        <div class="userWrapper">
                            <div class="userName">${user.name}</div>
                            <g:each in="${user.algos}" var="algo">
                                <div class="algo">
                                    <g:checkBox name="algo_${algo.id}" value="${sessionAlgos.collect {it.id}.contains(algo.id)}"/> ${algo.name}
                                    <g:if test="${currentUser.id == algo.user.id && algo.user.name != 'Guest'}">
                                        <g:link class="deleteAlgo" action="deleteAlgo" id="${algo.id}">delete</g:link>
                                    </g:if>
                                </div>
                            </g:each>
                        </div>
                    </g:if>
                </g:each>
                <a href="#" id="clearSelections">Clear Selections</a>
                <hr/>
                <div class="userName">
                    Grid Speed
                </div>
                <g:radioGroup values="${[5000, 2000, 1000, 500, 200, 100]}" labels="${['Wake me when its over', 'Super Slow', 'Slow', 'Normal', 'Fast', 'Ludicrous Speed']}"
                              name="speed" value="500">
                    ${it.radio} ${it.label}
                    <br/>
                </g:radioGroup>
                <br/><br/>
                <g:submitButton name="Generate Grid War"/>
            </g:form>
        </div>
    </div>
    <div class="uploads">
        <h3>OR Upload a closure program</h3>
        <div id="addAlgoWrapper">
            <g:uploadForm action="addAlgo">
                <div id="Entry Name">
                    <label>Program Name </label><g:textField name="algoName"></g:textField>
                </div>
                <div id="fileUpload">
                    <label>Program File </label><input type="file" name="algoFile"/>
                </div>
                <div id="textUpload">
                    <label>Program Text </label><g:textArea name="algoText" cols="120" rows="20"/>
                </div>
                <div>
                    <g:submitButton name="Add Program"/>
                </div>
            </g:uploadForm>
        </div>
    </div>
</div>


<script>
    $('#clearSelections').click(function(){
        $('input:checkbox').removeAttr('checked');
    });
</script>

</body>
</html>