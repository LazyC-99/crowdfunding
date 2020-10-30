<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">确认要删除下列角色?</h4>
            </div>
            <div class="modal-body">
                <div id="roleNameSpan" class="text-center"></div>
            </div>
            <div class="modal-footer">
                <button id="confirmRoleBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->