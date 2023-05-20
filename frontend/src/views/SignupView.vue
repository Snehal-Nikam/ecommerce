<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center pt-3">

      </div>
    </div>
    <div class="row">
      <div class="col-12 text-center pt-3">
        <div id="signup-div" class="flex-item-border">
          <h2 class="pt-4 pl-4">Create Account</h2>
          <form class="pt-4 pl-4 pr-4">
            <div class="form-group">
              <label for="Email">Email</label>
              <input type="email" class="form-control" required />
            </div>
            <div class="form-row">
              <div class="col">
                <div class="form-group">
                  <label> First Name </label>
                  <input type="text" class="form-control" required />
                </div>
              </div>
              <div class="col">
                <div class="form-group">
                  <label> Last Name </label>
                  <input type="text" class="form-control" required />
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="Password"> Password </label>
              <input type="password" class="form-control" required />
            </div>
            <div class="form-group">
              <label for="Password"> Confirm Password </label>
              <input type="password" class="form-control" required />
            </div>

            <button class="btn btn-primary mt-2"> Create Account </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
.btn-primary {
  background-color: #f0c14b;
  color: #000;
}
</style>
