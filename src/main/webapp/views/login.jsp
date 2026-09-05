<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row justify-content-center mt-4">
    <div class="col-md-4">
        <h3 class="text-center mb-3">Đăng nhập</h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label class="form-label">Email / Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100 mb-2">Đăng nhập</button>

            <div class="d-flex justify-content-between mt-2">
                <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                <a href="${pageContext.request.contextPath}/register">Đăng ký mới</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/footer.jsp" />