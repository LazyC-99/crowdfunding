<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="assignModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">角色维护</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin" role="form">
                    <div class="panel panel-default">
                        <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限分配列表<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                        <div class="panel-body">
                            <ul id="authTreeDemo" class="ztree"></ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="assignAuthBtn" type="button" class="btn btn-primary">分配</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->