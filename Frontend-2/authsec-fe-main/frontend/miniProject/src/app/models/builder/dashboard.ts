export interface WidgetModel {
  name: string;
  identifier: string;
}

export interface DashboardContentModel {
  cols: number;
  rows: number;
  y: number;
  x: number;
  chartid: number;
  component?: any;
  name: string;
}

export interface DashboardModel {
  id: number;
  username: string;
  dashboard: Array<DashboardContentModel>;
}

export const WidgetsMock: WidgetModel[] = [
  {
      name: 'Radar Chart',
      identifier: 'radar_chart'
  },
  {
      name: 'Doughnut Chart',
      identifier: 'doughnut_chart'
  },
  {
      name: 'Line Chart',
      identifier: 'line_chart'
  },
  {
  name: 'Bar Chart',
  identifier: 'bar_chart'
  },
  {
  name: 'Pie Chart',
  identifier: 'pie_chart'
  },
  {
  name: 'Polar Area Chart',
  identifier: 'polar_area_chart'
  },
  {
  name: 'Bubble Chart',
  identifier: 'bubble_chart'
  },
  {
  name: 'Scatter Chart',
  identifier: 'scatter_chart'
  },
  {
  name: 'Dynamic Chart',
  identifier: 'dynamic_chart'
  },
  {
  name: 'Financial Chart',
  identifier: 'financial_chart'
  },
  {
  name: 'To Do',
  identifier: 'to_do_chart'
  }
]
