(function ($) {
  var settings =  {};
  var defaults = {
    allowRemoval: true,
    tagClass: 'badge badge-dark mx-1'
  };
  
  $.fn.tagify = function(config) {
   settings = {...defaults, ...config};
     
   return this.each(function() {
     var $this = $(this);
     displayExistingTags($this);
     $this.keypress(createTagOnEnter);
   });
  };
  
  function displayExistingTags($el) {
    var tags = $el.data('tags');
       
    if (tags) {
      tags.forEach(function(tag) {
        $el.after(createTag(tag)).val('');
      });
    }
  }
  
  function createTagOnEnter(e) {
    if (e.which === 13) {
      e.preventDefault();
      var $this = $(this);
      $this.after(createTag($this.val().trim())).val('');
    }
  }
  
  function createTag(name) {
    if (settings.allowRemoval) {
      return $(`<span class="${settings.tagClass}">${name}<input type="hidden" value="${name}" name="tags"/> <a href="javascript://nop" onclick="$(this).closest('span').remove();">X</a></span>`);
    }
    else {
      return $(`<span class="small ${settings.tagClass}">${name}<input type="hidden" value="${name}" name="tags"/></span>`);
    }
  }
  
  // by default apply plugin to elements having data-provider=tagify 
  $('[data-provide="tagify"]').each(function(i, el) {
    var $this = $(this);
    var allowRemoval = $this.data('tagify-allow-removal');
    $this.tagify({
      allowRemoval: (allowRemoval !== undefined ? allowRemoval : true)
    });
  });
  
}(jQuery));