<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/views/decorators/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card border-0 shadow-sm rounded-3">
            <div class="card-body p-4">
                <h4 class="fw-bold mb-3"><i class="bi bi-person-circle me-2"></i>Hồ sơ cá nhân</h4>

                <c:if test="${not empty notice}">
                    <div class="alert alert-success">${notice}</div>
                </c:if>
                <c:if test="${not empty alert}">
                    <div class="alert alert-danger">${alert}</div>
                </c:if>

                <div class="text-center mb-3">
                    <img id="avatarPreview"
                         src="${pageContext.request.contextPath}/image?fname=${not empty user.images ? user.images : 'avata.png'}"
                         alt="avatar" width="140" height="140"
                         class="rounded-circle border object-fit-cover" />
                </div>

                <form action="${pageContext.request.contextPath}/profile" method="post"
                      enctype="multipart/form-data" class="needs-validation" novalidate>

                    <div class="mb-3">
                        <label class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" value="${user.username}" disabled />
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="text" class="form-control" value="${user.email}" disabled />
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Họ tên <span class="text-danger">*</span></label>
                        <input type="text" name="fullname" class="form-control"
                               value="${user.fullname}" required minlength="2" maxlength="200" />
                        <div class="invalid-feedback">Vui lòng nhập họ tên (2-200 ký tự)</div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Số điện thoại</label>
                        <input type="tel" name="phone" class="form-control"
                               value="${user.phone}" pattern="^0\d{9,10}$"
                               placeholder="VD: 0912345678" />
                        <div class="invalid-feedback">Số điện thoại phải bắt đầu bằng 0 và có 10-11 chữ số</div>
                    </div>

                    <div class="mb-4">
                        <label class="form-label">Ảnh đại diện</label>
                        <input type="file" name="images" class="form-control"
                               accept="image/png,image/jpeg,image/gif,image/webp"
                               onchange="previewImg(this)" />
                        <div class="form-text">Định dạng JPG, PNG, GIF, WEBP — tối đa 5MB</div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-check-circle me-1"></i>Cập nhật
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
function previewImg(input) {
    if (input.files && input.files[0]) {
        var file = input.files[0];
        var maxSize = 5 * 1024 * 1024;
        if (file.size > maxSize) {
            alert('Kích thước ảnh vượt quá 5MB, vui lòng chọn ảnh khác.');
            input.value = '';
            return;
        }
        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('avatarPreview').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

(function () {
    'use strict';
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
})();
</script>

<jsp:include page="/views/decorators/footer.jsp" />