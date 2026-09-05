<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-4">
        <h3 class="text-center mb-3">Đặt mật khẩu mới</h3>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <div class="mb-3">
                <label>Mật khẩu mới</label>
                <input type="password" name="newPassword" class="form-control" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary w-100">Lưu mật khẩu</button>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/footer.jsp" />