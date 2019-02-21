`strict`;

window.gruuf = {};

gruuf.bindEventTypes = function ( $eventTypes ) {
  const preloadedItems = $eventTypes.val().split(',');
  $eventTypes.val('');

  $eventTypes.selectize({
    valueField: 'id',
    labelField: 'name',
    searchField: 'name',
    sortField: 'name',
    create: false,
    preload: true,
    render: {
      option: function (item, escape) {
        return '<div><span class="name">' + escape(item.name) + '</span></div>';
      }
    },
    load: function (query, callback) {
      $.ajax({
        url: 'event-types',
        type: 'GET',
        error: function () {
          callback();
        },
        success: function (res) {
          callback(res);
        }
      });
    },
    onLoad: function (_) {
      const selectize = $eventTypes[0].selectize;
      selectize.setValue(preloadedItems);
    }
  });
};
