(function ($) {
  
 
  
  $.fn.duration = function() {
   return this.each(function() {
     createDurationInput($(this));
   });
  };
  
  function createDurationInput($el) {
    $el.addClass('d-none');
    
    var $group = $('<div class="input-group"></div>');
    var $sep = $('<div class="input-group-append"><span class="input-group-text">:</span></div>');
    var $hours = $('<input type="text" class="form-control" placeholder="hh"></input>');
    $hours.change(calcDuration);
    
    var $mins = $('<input type="text" class="form-control" placeholder="mm"></input>');
    $mins.change(calcDuration);
    
    var $secs = $('<input type="text" class="form-control" placeholder="ss"></input>');
    $secs.change(calcDuration);
    
    displayDuration($el.val());
    
    $group.append($hours, $sep.clone(), $mins, $sep.clone(), $secs);
    $el.after($group);
    
    function displayDuration(totalSeconds) {
      if (isNaN(totalSeconds) || totalSeconds === '') {
        return;
      }
      
      $hours.val(Math.floor(totalSeconds /(60 * 60)));
      $mins.val(Math.floor(totalSeconds % (60 * 60) / 60));
      $secs.val(Math.floor(totalSeconds % 60));
    }
    
    function calcDuration() {
      var hours = $hours.val();
      var mins = $mins.val();
      var secs = $secs.val();
      
      var totalSeconds = 0;
      
      if (!isNaN(hours)) {
        totalSeconds += hours * 60 * 60;
      }
      if (!isNaN(mins)) {
        totalSeconds += mins * 60;
      }
       if (!isNaN(secs)) {
        totalSeconds += secs * 1;
      }
      $el.val(totalSeconds === 0 ? '' : totalSeconds);
    }
  }
  
  // by default apply plugin to elements having data-provider=duration 
  $('[data-provide="duration"]').duration();
  
}(jQuery));