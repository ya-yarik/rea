var checkbox = document.querySelector("#check");
var input = document.querySelector("#categoriest");

var toogleInput = function(e){
    input.disabled = !e.target.checked;
};

toogleInput({target: checkbox});
checkbox.addEventListener("change", toogleInput);