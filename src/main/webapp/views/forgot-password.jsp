<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-4">
        <h3 class="text-center mb-3">Quên mật khẩu</h3>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
            <div class="mb-3">
                <label>Nhập Email đã đăng ký</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-warning w-100">Gửi OTP khôi phục</button>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/footer.jsp" />