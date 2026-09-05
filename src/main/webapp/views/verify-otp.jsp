<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-4">
        <h3 class="text-center mb-3">Xác thực mã OTP</h3>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/verify-otp" method="post">
            <div class="mb-3">
                <label>Mã OTP đã gửi qua email:</label>
                <input type="text" name="otp" class="form-control text-center fs-4" maxlength="6" required>
            </div>
            <button type="submit" class="btn btn-success w-100">Xác nhận</button>
        </form>
    </div>
</div>

<jsp:include page="/views/decorators/footer.jsp" />