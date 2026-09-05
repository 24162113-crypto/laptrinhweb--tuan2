<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-5">
        <h3 class="text-center mb-3">Đăng ký tài khoản</h3>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <c:if test="${not empty alert}"><div class="alert alert-danger">${alert}</div></c:if>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="mb-3">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" value="${username}" required>
            </div>
            <div class="mb-3">
                <label>Họ và tên</label>
                <input type="text" name="fullname" class="form-control" value="${fullname}" required>
            </div>
            <div class="mb-3">
                <label>Email</label>
                <input type="email" name="email" class="form-control" value="${email}" required>
            </div>
            <div class="mb-3">
                <label>Số điện thoại</label>
                <input type="text" name="phone" class="form-control" value="${phone}">
            </div>
            <div class="mb-3">
                <label>Mật khẩu</label>
                <input type="password" name="password" class="form-control" required minlength="6">
            </div>
            <div class="mb-3">
                <label>Xác nhận mật khẩu</label>
                <input type="password" name="confirmPassword" class="form-control" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary w-100">Gửi mã OTP</button>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/footer.jsp" />